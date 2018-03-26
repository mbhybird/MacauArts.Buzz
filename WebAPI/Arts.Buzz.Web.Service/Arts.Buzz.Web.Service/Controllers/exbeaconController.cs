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
    public class exbeaconController : BaseApiController
    {
        // GET api/exbeacon
        public IEnumerable<exbeacon> Getexbeacons()
        {
            return db.exbeacon.AsEnumerable();
        }

        // GET api/exbeacon/5
        public IEnumerable<exbeacon> Getexbeacon(string id)
        {
            IEnumerable<exbeacon> exbeacon = db.exbeacon.Where(x => x.extag == id);
            if (exbeacon == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return exbeacon;
        }

        // PUT api/exbeacon/5
        public HttpResponseMessage Putexbeacon(string id, exbeacon exbeacon)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != exbeacon.extag)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(exbeacon).State = EntityState.Modified;

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

        // POST api/exbeacon
        public HttpResponseMessage Postexbeacon(exbeacon exbeacon)
        {
            if (ModelState.IsValid)
            {
                db.exbeacon.Add(exbeacon);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, exbeacon);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = exbeacon.extag }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/exbeacon/5
        public HttpResponseMessage Deleteexbeacon(string id)
        {
            exbeacon exbeacon = db.exbeacon.Find(id);
            if (exbeacon == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.exbeacon.Remove(exbeacon);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, exbeacon);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}