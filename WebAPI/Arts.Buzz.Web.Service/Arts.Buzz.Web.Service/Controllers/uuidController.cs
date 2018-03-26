using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class uuidController : BaseApiController
    {
        // GET api/uuid
        public IEnumerable<beaconuuid> Getbeaconuuids()
        {
            return db.beaconuuid.AsEnumerable();
        }

        // GET api/uuid/5
        public beaconuuid Getbeaconuuid(int id)
        {
            beaconuuid beaconuuid = db.beaconuuid.Find(id);
            if (beaconuuid == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return beaconuuid;
        }

        // PUT api/uuid/5
        public HttpResponseMessage Putbeaconuuid(int id, beaconuuid beaconuuid)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != beaconuuid.id)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(beaconuuid).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/uuid
        public HttpResponseMessage Postbeaconuuid(beaconuuid beaconuuid)
        {
            if (ModelState.IsValid)
            {
                db.beaconuuid.Add(beaconuuid);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, beaconuuid);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = beaconuuid.id }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/uuid/5
        public HttpResponseMessage Deletebeaconuuid(int id)
        {
            beaconuuid beaconuuid = db.beaconuuid.Find(id);
            if (beaconuuid == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.beaconuuid.Remove(beaconuuid);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, beaconuuid);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}