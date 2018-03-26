using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Collections;

using Arts.Buzz.Web.Service.Models;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class repoController : BaseApiController
    {
        [HttpGet]
        //get app version
        public ViewAppVersion appversion()
        {
            var q = (from p in this.db.appversion
                     select new ViewAppVersion()
                     {
                         version = p.version,
                         url = p.url,
                         url_server = p.url_server,
                         description = p.description
                     }).SingleOrDefault();
            return q;
        }

        [HttpGet]
        //get data version
        public ViewCatalogVersion dataversion()
        {
            var q = (from p in this.db.appversion
                     select new ViewCatalogVersion()
                     {
                         publishedversion = p.publishedversion
                     }).SingleOrDefault();
            return q;
        }

        [HttpGet]
        //get beacon uuid
        public IEnumerable<beaconuuid> beaconuuid()
        {
            return this.db.beaconuuid;
        }

        [HttpGet]
        //get sysconfig
        public IEnumerable<sysconfig> sysconfig()
        {
            return this.db.sysconfig;
        }

        [HttpGet]
        //get catalog
        public IEnumerable<exmaster_ex> catalog()
        {
            var q = from c in db.content
                    from exc in db.excontent.Where(x => x.usage == "0")
                    where exc.contentid == c.contentid
                    from ex in db.exmaster
                    where ex.extag == exc.extag && System.Data.Objects.EntityFunctions.DiffDays(ex.datefrom, DateTime.Now) >= 0
                    && System.Data.Objects.EntityFunctions.DiffDays(ex.dateto, DateTime.Now) <= 0
                    select new exmaster_ex()
                    {
                        content = c,
                        creator = ex.creator,
                        datefrom = ex.datefrom,
                        dateto = ex.dateto,
                        extag = ex.extag,
                        location = ex.location,
                        publish = ex.publish,
                        website = ex.website,
                        title_cn = ex.title_cn,
                        title_en = ex.title_en,
                        title_pt = ex.title_pt,
                        title_tw = ex.title_tw,
                        description_cn = ex.description_cn,
                        description_en = ex.description_en,
                        description_pt = ex.description_pt,
                        description_tw = ex.description_tw,
                        fileCount = db.excontent.Where(x => x.extag == ex.extag && x.usage == "1").Count()
                    };

            return q;
        }

        [HttpGet]
        //get excontent
        public ViewExContent excontent(string id)
        {
            var beacons = (from b in db.beacon
                           from ex in this.db.exbeacon.Where(x => x.extag == id)
                           where b.beaconid == ex.beaconid
                           select b).ToList();

            var contents = (from c in db.content
                            from ex in this.db.excontent.Where(x => x.extag == id)
                            where c.contentid == ex.contentid
                            select new content_ex
                            {
                                contentid = c.contentid,
                                contenttype = c.contenttype,
                                usage = ex.usage,
                                serverpath = c.serverpath,
                                clientpath = c.clientpath,
                                filename = c.filename,
                                year = c.year,
                                title_cn = c.title_cn,
                                title_en = c.title_en,
                                title_pt = c.title_pt,
                                title_tw = c.title_tw,
                                artist_cn = c.artist_cn,
                                artist_en = c.artist_en,
                                artist_pt = c.artist_pt,
                                artist_tw = c.artist_tw,
                                description_cn = c.description_cn,
                                description_en = c.description_en,
                                description_pt = c.description_pt,
                                description_tw = c.description_tw,
                                range = c.range
                            }).ToList();

            var aclist = (from b in beacons
                          from ac in db.beaconaction
                          where b.beaconid == ac.beaconid
                          select ac).ToList();

            var tglist = db.trigger.ToList();

            beacons.ForEach(b =>
            {
                var q = (from ac in aclist.Where(x => x.beaconid == b.beaconid)
                         select new TriggerContent()
                         {
                             trigger = tglist.FirstOrDefault(x => x.triggerid == ac.triggerid),
                             content = contents.FirstOrDefault(x => x.contentid == ac.contentid)
                         });

                b.triggercontent = q.ToList();
            });


            return new ViewExContent() { beacons = beacons, contents = contents };
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}
